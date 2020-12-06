
$(function () {
    $('.FirstClass').change(function () {
        var id = $(this).find(':selected').val();
        // alert(id);
        $.ajax({
            url: '/getSecondClass.do',
            type: 'POST',
            dataType: 'JSON',
            data: {id: id},
            success: function (result) {
                var SecondClass = $('.SecondClass');
                SecondClass.empty();
                for (var i = 0; i < result.length; i++) {
                    SecondClass.append('<option id=' + result[i].id + ' value=' + result[i].id + '>' + result[i].name + '</option>');
                }
                var cid = $('.SecondClass').find(':selected').val();
                $.ajax({
                    url:'/getThirdClass.do',
                    type:'POST',
                    dataType:'JSON',
                    data:{id:cid},
                    success:function (result) {
                        var ThirdClass = $('.ThirdClass');
                        ThirdClass.empty();
                        for (var i = 0; i<result.length;i++) {
                            ThirdClass.append('<option id=' + result[i].id + ' value=' + result[i].id + '>' + result[i].name + '</option>');
                        }
                    }
                });
            },
        });

    });
    $('.SecondClass').change(function () {
        var id = $(this).find(':selected').val();
        $.ajax({
           url:'/getThirdClass.do',
            type:'POST',
            dataType:'JSON',
            data:{id:id},
            success:function (result) {
                var ThirdClass = $('.ThirdClass');
                ThirdClass.empty();
                for (var i = 0; i<result.length;i++) {
                    ThirdClass.append('<option id=' + result[i].id + ' value=' + result[i].id + '>' + result[i].name + '</option>');
                }
            }
        });
    });

});