function checkScreenWidth() {
    if (window.innerWidth < 1900 && window.innerHeight < 900) {
        document.getElementById('unsupported-screen').style.display = 'block';
        document.getElementById('maxBox').style.display = 'none';
    } else {
        document.getElementById('unsupported-screen').style.display = 'none';
        document.getElementById('maxBox').style.display = 'block';
    }
}

// 当窗口大小变化时检查屏幕宽度
window.addEventListener('resize', checkScreenWidth);

// 初始检查
checkScreenWidth();

let vm = new Vue({
    el: '#maxBox',
    data: {
        firstColumn: [],
        secondColumn: [],
        wordList: [],
        chinese: "",
        level: [],
        selectedLevel: null, // 添加这一行
        url: "http://localhost:8080/game/getWords?d=",
        url2: "http://localhost:8080/game/getClassify",
        param: "level_6_vocabulary"
    },
    methods: {
        getWords() {
            let self = this; // 保存 Vue 实例的引用
            //http://localhost:8080/game/getWords
            $.get(this.url + this.param, {"action": "getWords"}, function (data) {
                var wordList = data.extend.wordList;
                self.wordList = wordList;
                var randomInt = getRandomInt(0, 3);
                self.chinese = wordList[randomInt].chinese;
            })
        },
        getCla() {
            let self = this; // 保存 Vue 实例的引用
            $.get(this.url2, {"action": "getClassify"}, function (data) {
                self.level = data.extend.claList;
                if (self.level.length > 0) {
                    self.selectedLevel = self.level[0]; // 设置默认选中第一项
                }
            })
        }
    },
    watch: {
        selectedLevel(newVal, oldVal) {
            // 当选中项发生变化时，你可以在这里处理
            console.log('选中的难度是：', newVal.table);
            this.param = newVal.table;
            this.getWords()
        }
    }
})

$(document).ready(function () {
    //vm.getWords();
    vm.getCla();

    var planStep = 80; // 设置飞机每次移动的步长
    var bulletSpeed = 5; // 设置子弹移动的速度

    var baseStep = 1; // 设置基础步长
    var moveIntervals = []; // 用于存储多个定时器的数组
    var isMoving = false; // 初始状态为未移动

    // 获取#box和#plan的宽度，用于计算移动边界
    var boxWidth = $('#box').width();
    var planWidth = $('#plan').width();

    // 开始/暂停/继续移动的逻辑
    $('#spc').click(function () {
        if (!isMoving) {
            // 如果当前未在移动，则开始移动
            isMoving = true;
            $('#transmit').prop('disabled', false); // 启用发射按钮
            $('.english_word_module').each(function (index) {
                var movingRight = true; // 初始方向向右
                var elementStep = baseStep * (index + 1); // 每个元素的步长基于它的索引

                // 设置定时器移动每个单词
                moveIntervals[index] = setInterval(function () {
                    var currentLeft = parseInt($(this).css('left'), 10);
                    var newLeft;
                    if (movingRight) {
                        newLeft = currentLeft + elementStep;
                        if (newLeft >= boxWidth - $(this).width()) {
                            movingRight = false;
                        }
                    } else {
                        newLeft = currentLeft - elementStep;
                        if (newLeft <= 0) {
                            movingRight = true;
                        }
                    }
                    $(this).css('left', newLeft);
                }.bind(this), 20);
            });
        } else {
            // 如果当前在移动，则暂停移动
            $('#transmit').prop('disabled', true); // 禁用发射按钮
            $.each(moveIntervals, function (index, interval) {
                clearInterval(interval);
            });
            isMoving = false;
        }
    });

    // 结束游戏的逻辑
    $('#stop').click(function () {
        // 停止移动
        $.each(moveIntervals, function (index, interval) {
            clearInterval(interval);
        });
        moveIntervals = []; // 清空定时器数组
        isMoving = false;
        // 将所有单词恢复到初始位置
        $('.english_word_module').css('left', '0');
        $('#transmit').prop('disabled', true); // 禁用发射按钮
    });

    // 左移按钮点击事件
    $('#toLeft').click(function () {
        var planPos = $('#plan').position();
        var newLeft = Math.max(planPos.left - planStep, 0);
        $('#plan').css('left', newLeft);
    });

    // 右移按钮点击事件
    $('#toRight').click(function () {
        var planPos = $('#plan').position();
        var newRight = Math.min(planPos.left + planStep, boxWidth - planWidth);
        $('#plan').css('left', newRight);
    });

    // 发射按钮点击事件
    $('#transmit').click(function () {
        if (isMoving) {
            // 创建子弹
            var bullet = $('<div><img width="40px" src="img/zidan.png"></div>');
            bullet.css({
                position: 'absolute',
                bottom: '80px', // 飞机的高度
                left: $('#plan').position().left + 53.5 + 'px', // 飞机当前的水平位置
                width: '40px', // 子弹的宽度
                height: '40px', // 子弹的高度
                //backgroundColor: 'red' // 子弹的颜色
            });

            // 将子弹添加到#box中
            $('#box').append(bullet);

            // 子弹向上移动的动画
            function moveBullet() {
                // 检查子弹是否还在DOM中
                if (bullet.parent().length === 0) {
                    return; // 如果子弹已经被移除，则停止动画
                }

                var bulletPos = bullet.position();
                if (bulletPos.top > -10) {
                    bullet.css('top', bulletPos.top - bulletSpeed + 'px');
                    checkCollision(bullet); // 检测碰撞
                    requestAnimationFrame(moveBullet); // 递归调用，创建动画效果
                } else {
                    bullet.remove(); // 当子弹超出#box顶部时移除子弹
                }
            }

            requestAnimationFrame(moveBullet); // 开始子弹的移动
        }

    });
    // 初始化时禁用发射按钮
    $('#transmit').prop('disabled', true);

    // 碰撞检测函数
    function checkCollision(bullet) {
        var bulletOffset = bullet.offset();
        var bulletX = bulletOffset.left;
        var bulletY = bulletOffset.top;
        var bulletWidth = bullet.width();
        var bulletHeight = bullet.height();

        $('.english_word_module').each(function () {
            var wordModule = $(this);
            var wordOffset = wordModule.offset();
            var wordX = wordOffset.left;
            var wordY = wordOffset.top;
            var wordWidth = wordModule.width();
            var wordHeight = wordModule.height();

            if (bulletX < wordX + wordWidth &&
                bulletX + bulletWidth > wordX &&
                bulletY < wordY + wordHeight &&
                bulletY + bulletHeight > wordY) {
                // 碰撞发生
                handleCollision(bullet, wordModule); // 处理碰撞
            }
        });
    }

    function handleCollision(bullet, wordModule) {
        // 移除子弹
        bullet.remove();

        // 获取单词文本
        var wordEnglish = wordModule.text().trim(); // 清除空格
        var wordChinese = vm.$data.chinese.trim();

        // 暂停游戏
        pauseGame();

        var wordChinese2 = "";
        var wordList = vm.$data.wordList;

        //击中的单词对象
        var d;
        //正确的单词对象
        var d2;
        for (let i = 0; i < wordList.length; i++) {
            if (wordList[i].word === wordEnglish){
                wordChinese2 = wordList[i].chinese;
                d = wordList[i];
            }
            if (wordChinese == wordList[i].chinese){
                d2 = wordList[i];
            }
        }

        var resultText = '结果：错误';
        if (d.word == d2.word){
            resultText = '结果：正确';
        }
        $('#resultInfo').text(resultText);
        // 更新模态框信息
        $('#wordInfo').text('单词：' + wordEnglish);
        $('#bulletInfo').text('子弹：' + vm.$data.chinese);

        // 对比中文翻译，判断结果是否正确
        var resultText = '结果：错误';

        // 显示正确的英文单词
        $('#correctAnswer').text('该中文对应的正确单词为：' + d2.word);

        // 显示模态框
        $('#collisionModal').show();
    }



    function pauseGame() {
        // 如果当前在移动，则暂停移动
        $('#transmit').prop('disabled', true); // 禁用发射按钮
        $.each(moveIntervals, function (index, interval) {
            clearInterval(interval);
        });
        isMoving = false;
    }


    // 绑定模态框按钮事件
    $('#playAgain').click(function () {
        // 隐藏模态框
        $('#collisionModal').hide();

        // 模拟点击结束按钮和开始按钮
        $('#stop').click(); // 假设结束按钮的 ID 是 'stop'
        setTimeout(function () {
            $('#spc').click(); // 假设开始按钮的 ID 是 'spc'
        }, 100); // 设置一个小延迟确保结束逻辑已经执行
    });


    $('#nextLevel').click(function () {
        // 隐藏模态框
        $('#collisionModal').hide();

        // 刷新页面
        //location.reload();

        ///*模拟点击结束游戏*/
        //$('#stop').click();
        ///*获取新单词*/
        //vm.getWords();
        //$('#spc').click(); // 假设开始按钮的 ID 是 'spc'
        vm.getWords();
        // 模拟点击结束按钮和开始按钮
        $('#stop').click(); // 假设结束按钮的 ID 是 'stop'
        //setTimeout(function () {
        //    $('#spc').click(); // 假设开始按钮的 ID 是 'spc'
        //}, 100); // 设置一个小延迟确保结束逻辑已经执行
    });


    $('#endGame').click(function () {
        // 隐藏模态框
        $('#collisionModal').hide();

        // 模拟点击结束按钮来结束游戏
        $('#stop').click(); // 假设结束按钮的 ID 是 'stop'
    });

    //vm.getWords();
    //vm.getCla();
});

function getRandomInt(min, max) {
    // 将参数转换为整数，以防传入的是字符串或者小数
    min = Math.ceil(min);
    max = Math.floor(max);
    // Math.random() 返回[0, 1)之间的一个随机数
    // 所以这里的计算是为了确保返回的数值是大于等于min且小于等于max
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
