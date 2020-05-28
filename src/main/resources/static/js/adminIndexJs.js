$(function () {
    adminIndexJs.bindEvent();
});
var adminIndexJs = {
    bindEvent: function () {
        if ($("#role").val() == 1) {
            $("#admin-tab").addClass("layui-hide");
        } else {
            $("#admin-tab").removeClass("layui-hide");
        }
        adminIndexJs.event.questionList();
        adminIndexJs.event.importStudentList();
    },
    event: {
        questionList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#question-list").removeClass('layui-hide');
                $("#student-list").addClass('layui-hide');
                $("#admin-list").addClass('layui-hide');
                $("#answer-list").addClass('layui-hide');
                table.render({
                    elem: '#question-list-table'
                    , height: 485
                    , url: '../question/question-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: 'ID', width: 70}
                        , {field: 'question_text', title: '题目', width: 180}
                        , {field: 'answer_a', title: '选项A', width: 100}
                        , {field: 'answer_b', title: '选项B', width: 100}
                        , {field: 'answer_c', title: '选项C', width: 100}
                        , {field: 'answer_d', title: '选项D', width: 100}
                        , {field: 'correct_answer', title: '正确答案', width: 100}
                        , {field: 'project_name', title: '类型', width: 100}
                        , {field: 'status_str', title: '状态', width: 100}
                        , {
                            field: 'operate',
                            title: '操作',
                            width: 200,
                            toolbar: "#question-list-table-operate"
                        }
                    ]]
                });
                table.on('tool(question-list-table-fit)', function (obj) {
                    if (obj.event === 'del') {
                        layer.confirm('确定删除该题目？', function (index) {
                            $.ajax({
                                url: '../question/question-delete',
                                data: {
                                    question_id: obj.data.id
                                },
                                type: 'get',
                                success: function () {
                                    layer.msg("删除成功");
                                    adminIndexJs.event.questionList();
                                },
                                error: function () {
                                    layer.msg("数据请求异常");
                                    layer.closeAll()
                                }
                            })
                        })
                    } else {
                        $.ajax({
                            url: '../question/question-get',
                            data: {
                                question_id: obj.data.id
                            },
                            type: 'get',
                            success: function (result) {
                                $("#edit-id").val(result.vo.id);
                                $("#edit-project").val(result.vo.project_name);
                                $("#edit-question-text").val(result.vo.question_text);
                                $("#edit-answer-a").val(result.vo.answer_a);
                                $("#edit-answer-b").val(result.vo.answer_b);
                                $("#edit-answer-c").val(result.vo.answer_c);
                                $("#edit-answer-d").val(result.vo.answer_d);
                                $("#edit-correct-answer").val(result.vo.correct_answer);
                                layui.use(['layer', 'form'], function (layer, form) {
                                    layer.open({
                                        type: 1
                                        , skin: 'examine-refuse-popup'
                                        , offset: 'auto'
                                        , title: '编辑题目'
                                        , id: 'layer-id'
                                        , area: ['500px', '600px']
                                        , content: $("#dialog-edit-question-info")
                                        , btn: ['确定', '取消']
                                        , shade: 0.5 //不显示遮罩
                                        , end: function () {
                                            $("#dialog-edit-question-info").css("display", "none");
                                        }
                                        , yes: function () {
                                            adminIndexJs.method.updateQuestion();
                                        },
                                        btn2: function () {

                                        }
                                    });
                                });
                            }
                        })
                    }
                })
            });
        },
        importStudentList: function () {
            layui.use('upload', function(){
                var upload = layui.upload;
                //执行实例
                var uploadInst = upload.render({
                    elem: '#import-student-btn'
                    ,accept: 'file'
                    ,exts: 'csv'
                    ,url: '../student/import-list'
                    ,before: function(obj){
                        layer.load();
                    }
                    ,done: function(res){
                        if (res.status_code == 200) {
                            layer.msg("导入成功")
                            layer.closeAll();
                        } else {
                            layer.closeAll();
                        }
                        adminIndexJs.event.questionList();
                    }
                    ,error: function(){
                        layer.closeAll();
                    }
                });
            });
        }
    },
    method: {
        updateQuestion: function () {
            var data = {};
            data.id = $("#edit-id").val();
            data.question_text = $("#edit-question-text").val();
            data.answer_a = $("#edit-answer-a").val();
            data.answer_b = $("#edit-answer-b").val();
            data.answer_c = $("#edit-answer-c").val();
            data.answer_d = $("#edit-answer-d").val();
            data.correct_answer = $("#edit-correct-answer").val();
            $.ajax({
                url: '../question/question-update',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    layer.msg("修改成功");
                    layer.closeAll()
                    adminIndexJs.event.questionList();
                },
                error: function () {
                    layer.msg("数据请求异常");
                    layer.closeAll()
                }
            })
        },
        addQuestionDialog: function () {
            layui.use('layer', function (layer) {
                layer.open({
                    type: 1
                    , skin: 'examine-refuse-popup'
                    , offset: 'auto'
                    , title: '新增题目'
                    , id: 'layer-id'
                    , area: ['500px', '600px']
                    , content: $("#dialog-add-question-info")
                    , btn: ['确定', '取消']
                    , shade: 0.5 //不显示遮罩
                    , end: function () {
                        $("#dialog-add-question-info").css("display", "none");
                    }
                    , yes: function () {
                        adminIndexJs.method.addQuestion();
                    }
                    , btn2: function () {

                    }
                });
            });
        },
        addQuestion: function () {
            layer.close(layer.index);
            var data = {};
            data.project_id = $("#add-project").val();
            data.project_name = $("#add-project option:selected").text();
            data.question_text = $("#add-question-text").val();
            data.answer_a = $("#add-answer-a").val();
            data.answer_b = $("#add-answer-b").val();
            data.answer_c = $("#add-answer-c").val();
            data.answer_d = $("#add-answer-d").val();
            data.correct_answer = $("#add-correct-answer").val();
            $.ajax({
                url: '../question/question-add',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    if (result.status_code == 200) {
                        layer.msg('添加题目成功');
                        adminIndexJs.event.questionList();
                    } else {
                        layer.msg(result.message);
                    }
                },
                error: function () {
                    layer.msg('数据异常');
                    layer.closeAll();
                }
            })
        },
        studentList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#student-list").removeClass('layui-hide');
                $("#question-list").addClass('layui-hide');
                $("#admin-list").addClass('layui-hide');
                $("#answer-list").addClass('layui-hide');
                table.render({
                    elem: '#student-list-table'
                    , height: 485
                    , url: '../student/student-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: 'ID', width: 70}
                        , {field: 'user_name', title: '姓名', width: 160}
                        , {field: 'email', title: '邮箱', width: 260}
                        , {field: 'create_time', title: '注册时间', width: 240, templet:'<div>{{ layui.util.toDateString(d.create_time, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                        , {field: 'password', title: '密码（暗文）', width: 270}
                        , {field: 'status_str', title: '状态', width: 120}
                    ]]
                });
            });
        },
        adminList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#admin-list").removeClass('layui-hide');
                $("#student-list").addClass('layui-hide');
                $("#question-list").addClass('layui-hide');
                $("#answer-list").addClass('layui-hide');
                table.render({
                    elem: '#admin-list-table'
                    , height: 485
                    , url: '../admin/admin-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: 'ID', width: 70}
                        , {field: 'account_name', title: '管理员名称', width: 130}
                        , {field: 'password', title: '管理员密码（暗文）', width: 200}
                        , {field: 'role_name', title: '角色名', width: 100}
                        , {field: 'create_time', title: '注册时间', width: 170, templet:'<div>{{ layui.util.toDateString(d.create_time, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                        , {field: 'update_time', title: '修改时间', width: 170, templet:'<div>{{ layui.util.toDateString(d.update_time, "yyyy-MM-dd HH:mm:ss") }}</div>'}
                        , {field: 'status_str', title: '状态', width: 80}
                        , {
                            field: 'operate',
                            title: '操作',
                            width: 200,
                            toolbar: "#admin-list-table-operate"
                        }
                    ]]
                });
                table.on('tool(admin-list-table-fit)', function (obj) {
                    if (obj.event === 'del') {
                        layer.confirm('确定删除该管理员？', function (index) {
                            $.ajax({
                                url: '../admin/admin-delete',
                                data: {
                                    id: obj.data.id
                                },
                                type: 'get',
                                success: function () {
                                    layer.msg("删除成功");
                                    adminIndexJs.method.adminList();
                                },
                                error: function () {
                                    layer.msg("数据请求异常");
                                    layer.closeAll()
                                }
                            })
                        })
                    } else {
                        $.ajax({
                            url: '../admin/admin-get',
                            data: {
                                id: obj.data.id
                            },
                            type: 'get',
                            success: function (result) {
                                $("#edit-admin-id").val(result.vo.id);
                                $("#edit-username").val(result.vo.account_name);
                                $("#edit-password").val(result.vo.password);
                                layui.use(['layer', 'form'], function (layer, form) {
                                    layer.open({
                                        type: 1
                                        , skin: 'examine-refuse-popup'
                                        , offset: 'auto'
                                        , title: '编辑管理员'
                                        , id: 'layer-id'
                                        , area: ['550px', '300px']
                                        , content: $("#dialog-edit-admin-info")
                                        , btn: ['确定', '取消']
                                        , shade: 0.5 //不显示遮罩
                                        , end: function () {
                                            $("#dialog-edit-admin-info").css("display", "none");
                                        }
                                        , yes: function () {
                                            adminIndexJs.method.updateAdmin();
                                        },
                                        btn2: function () {

                                        }
                                    });
                                });
                            }
                        })
                    }
                })
            });
        },
        updateAdmin: function () {
            var data = {};
            data.id = $("#edit-admin-id").val();
            data.account_name = $("#edit-username").val();
            data.password = $("#edit-password").val();
            $.ajax({
                url: '../admin/admin-update',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (result) {
                    layer.msg("修改管理员信息成功");
                    layer.closeAll();
                    adminIndexJs.method.adminList();
                },
                error: function () {
                    layer.msg("数据请求异常");
                    layer.closeAll();
                }
            })
        },
        addAdminDialog: function () {
            layui.use('layer', function (layer) {
                layer.open({
                    type: 1
                    , skin: 'examine-refuse-popup'
                    , offset: 'auto'
                    , title: '添加管理员'
                    , id: 'layer-id'
                    , area: ['550px', '300px']
                    , content: $("#dialog-add-admin-info")
                    , btn: ['确定', '取消']
                    , shade: 0.5 //不显示遮罩
                    , end: function () {
                        $("#dialog-add-admin-info").css("display", "none");
                    }
                    , yes: function () {
                        adminIndexJs.method.addAdmin();
                    }
                    , btn2: function () {

                    }
                });
            });
        },
        addAdmin: function () {
            layer.closeAll();
            var data = {};
            data.id = $("#add-admin-id").val();
            data.password = $("#add-password").val();
            data.account_name = $("#add-username").val();
            $.ajax({
                url: '../admin/admin-add',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function () {
                    layer.msg("添加成功");
                    adminIndexJs.method.adminList();
                },
                error: function () {
                    layer.msg("数据请求异常");
                    layer.closeAll();
                }
            })
        },
        answerList: function () {
            layui.use('table', function () {
                var table = layui.table;
                $("#answer-list").removeClass('layui-hide');
                $("#student-list").addClass('layui-hide');
                $("#question-list").addClass('layui-hide');
                $("#admin-list").addClass('layui-hide');
                table.render({
                    elem: '#answer-list-table'
                    , height: 485
                    , url: '../answer/answer-list'
                    , page: true //开启分页
                    , limits: [5, 10, 20]
                    , limit: 10
                    , cols: [[ //表头
                        {field: 'id', title: 'ID', width: 70}
                        , {field: 'user_name', title: '学生姓名', width: 130}
                        , {field: 'question_text', title: '问题', width: 350}
                        , {field: 'choose_answer', title: '作答', width: 150}
                        , {field: 'correct_answer', title: '正确答案', width: 150}
                        , {
                            field: 'create_time',
                            title: '作答时间',
                            width: 170,
                            templet: '<div>{{ layui.util.toDateString(d.create_time, "yyyy-MM-dd HH:mm:ss") }}</div>'
                        }
                        , {field: 'status_str', title: '状态', width: 100}
                    ]]
                });
            })
        },
        exportAnswerList: function () {
            var url = "../answer/export";
            window.open(url);
        }

    }
}
