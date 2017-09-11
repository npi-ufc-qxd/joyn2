
!function($) {
    "use strict";

    var SweetAlert = function() {};
    

    //examples 
    SweetAlert.prototype.init = function() {
        
    //Basic
    $('#sa-basic').click(function(){
        swal("Here's a message!");
    });

    //A title with a text under
    $('#sa-title').click(function(){
        swal("Here's a message!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lorem erat eleifend ex semper, lobortis purus sed.")
    });

    //Success Message
    $('#sa-success').click(function(){
        swal("Good job!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lorem erat eleifend ex semper, lobortis purus sed.", "success")
    });

    //Warning Message
    $('.sa-warning-atividade').click(function(){
    	var id = $(this).attr('value');
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar atividade?",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/atividade/excluir/'+id;    	    
        });
    });
    
    //Warning Message QRCODE
    $('.sa-warning-gerar-qr').click(function(){
    	var id = $(this).attr('th.object');
    	console.log(id.id);
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar atividade?",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/atividade/excluir/'+id;    	    
        });
    });
  
    //Warning Message
    $('.sa-warning-organizador').click(function(){
    	var id = $(this).attr('value');
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar este organizador?\n Você será o novo organizador das atividade que este usuario organizava!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/evento/excluir_organizador/'+id;    	    
        });
    });
    
    //Warning Message
    $('.sa-warning-organizadorb').click(function(){
    	var id = $(this).attr('value');
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar este organizador?\n Você será o novo organizador das atividade que este usuario organizava!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/evento/excluir_participantes/'+id;    	    
        });
    });
    
    //Warning Message
    $('.sa-warning-organizador-atividade').click(function(){
    	var id = $(this).attr('value');
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar este organizador?\n Você será o novo organizador das atividade que este usuario organizava!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/atividade/excluir_participante/'+id;    	    
        });
    });
    
    //Warning Message
    $('.sa-warning-participante-atividadeb').click(function(){
    	var id = $(this).attr('value');
        swal({   
            title: "Atenção!",   
            text: "Tem certeza que deseja deletar este organizador?\n Você será o novo organizador das atividade que este usuario organizava!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Deletar",   
            closeOnConfirm: false,
        }, function(){
        	window.location = '/evento/excluir_participantes/'+id;    	    
        });
    });
  
    //Parameter
    $('#sa-params').click(function(){
        swal({   
            title: "Are you sure?",   
            text: "You will not be able to recover this imaginary file!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Yes, delete it!",   
            cancelButtonText: "No, cancel plx!",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){   
            if (isConfirm) {     
                swal("Deleted!", "Your imaginary file has been deleted.", "success");   
            } else {     
                swal("Cancelled", "Your imaginary file is safe :)", "error");   
            } 
        });
    });

    //Custom Image
    $('#sa-image').click(function(){
        swal({   
            title: "Govinda!",   
            text: "Recently joined twitter",   
            imageUrl: "../plugins/images/users/govinda.jpg" 
        });
    });

    //Auto Close Timer
    $('#sa-close').click(function(){
         swal({   
            title: "Auto close alert!",   
            text: "I will close in 2 seconds.",   
            timer: 2000,   
            showConfirmButton: false 
        });
    });


    },
    //init
    $.SweetAlert = new SweetAlert, $.SweetAlert.Constructor = SweetAlert
}(window.jQuery),

//initializing 
function($) {
    "use strict";
    $.SweetAlert.init()
}(window.jQuery);