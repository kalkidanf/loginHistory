$(document).ready(function() {	
	
	
	
	
	
	
	
	
   var table= $('#user').DataTable( {
	   "bLengthChange": false,
	   "oLanguage": {
		   "sEmptyTable": "user not found!"
		   },
       "order": [[ 5, "asc" ]],
       "columnDefs": [
                      { "orderable": false, "targets": 0 }
                    ]    
    } );  
 

   $('#firstName').on( 'keyup', function () {
	    table
	        .columns(1)
	        .search( this.value )
	        .draw();
	} );

	$('#lastName').on( 'keyup', function () {
	    table
	        .columns(2)
	        .search( this.value )
	        .draw();
	} );
	
	
	$(function() {
	    $( "#dp" ).datepicker();
	  });
	if($('#loginDate').val()=="never")
		{
		
		$( "#lastLoginDate" ).hide('fast');
		}
	else if($('#loginDate').val()=="before"){
		
		$( "#lastLoginDate" ).show('fast');
	    }
	else if($('#loginDate').val()=="after"){
		
		$( "#lastLoginDate" ).show('fast');
	    }
	else if($('#loginDate').val()=="on"){
		
		$( "#lastLoginDate" ).show('fast');
	    }
	$('#selectAllUsers').click(function(){
		if($(this).prop('checked')==true){
		
			$('.selectSingleUser').prop('checked', $(this).prop('checked'));
			$('#activateDeactivate').prop('disabled', false);
		}		
		
	
		else if($(this).prop('checked')==false)
			{
			$('.selectSingleUser').each(function(){
				this.checked=false;
				$('#activateDeactivate').prop('disabled',true);
			});
			}
	});
	
	  var status = $('#status').val();
	    var rowCount = $('#user >tbody >tr').length;
	    
	   if(rowCount==1)
		   {
		   $('#selectAllUsers').hide('fast');
		   }
	   else
		   {
		   $('#selectAllUsers').show('show');
		   }
	   
	$('#loginDate').click(function(){
		
		if($('#loginDate').val()=="never")
		{
		
		$( "#lastLoginDate" ).hide('fast');
		}
	else if($('#loginDate').val()=="before"){
			
		$( "#lastLoginDate" ).show('fast');
	    }
	else if($('#loginDate').val()=="after"){
			
		$( "#lastLoginDate" ).show('fast');
	    }
	else if($('#loginDate').val()=="on"){
			
		$( "#lastLoginDate" ).show('fast');
	    }
	});
	$('.selectSingleUser').click(function(){
		if($(this).prop('checked')==true){
			$('#activateDeactivate').prop('disabled',false);
		}
		if ($('.selectSingleUser:checked').size() == 0)
			{
			$('#activateDeactivate').prop('disabled',true);
			$('#selectAllUsers').prop('checked',false);
			}
		
	});

$('#search').click(function(){
	
});	

	
} );