$(document)
		.ready(
				function() {

					// Hide errors
					$("#specCodeError").hide();
					$("#specNameError").hide();
					$("#specNoteError").hide();

					// Define error variable
					var specCodeError = false;
					var specNameError = false;
					var specNoteError = false;

					// validate field
					function validate_specCode() {
						var val = $("#specCode").val();
						var exp = /^[A-Z]{4,10}$/;
						
						if (val == "") {
							$("#specCodeError").show()
							$("#specCodeError").html("* Please Enter code")
							$("#specCodeError").css("color", "red")

							specCodeError = false
							
						} else if (!exp.test(val)) {
							$("#specCodeError").show();
							$("#specCodeError").html("*<b>Code</b> Can not be empty")
							$("#specCodeError").css('color', 'red');
							
							specCodeError = false;
							
						} else {
							var id = 0;
							if($("#id").val()!=undefined){
								specCodeError = true;
								id = $("#id").val();
							}
							
							$.ajax({
									url:'checkCode',
									data:{"code":val, "id":id},
									success:function(resp) {
								if (resp != '') {
									$("#specCodeError").show();
									$("#specCodeError").html(resp);	
									$("#specCodeError").css('color','red');	
										
									specCodeError = false;
									} else {
										$("#specCodeError").hide();
										
										specCodeError = true;
											}
										}
									});
						}
						
						return specCodeError
					}

					function validate_specName() {
						
						var val = $("#specName").val()
						var exp = /^[A-Za-z0-9\s\.]{4,60}$/;
						
						if (val == "") {
							$("#specNameError").show()
							$("#specNameError").html("* Please Enter Specialisation name")
							$("#specNameError").css("color", "red")

							specNameError = false
							
						} else if (!exp.test(val)) {
							
							$("#specNameError").show();
							$("#specNameError").html("*<b>Name</b> must be 4-25 chars")		
							$("#specNameError").css('color', 'red');
							
							specNameError = false;
						} else {
							
							$.ajax({
								url:"checkName",
								data:{"name": val},
								success:function(resp){
									if(resp !=''){
										$("#specNameError").show()
										$("#specNameError").html(resp)
										$("#specNameError").css('color','red')
										
										specNameError =false
									}
								}
							});
							
							
							$("#specNameError").hide()
							specNameError = true
						}
						return specNameError
					}

					function validate_specNote() {

						var val = $("#specNote").val();
						var exp = /^[A-Za-z0-9\s\.\-\,\']{10,250}$/;
						if (val == "") {

							$("#specNoteError").show()
							$("#specNoteError").html("* Please Enter note")
							$("#specNoteError").css("color", "red")

							specNoteError = false
						} else if (!exp.test(val)) {
							$("#specNoteError").show();
							$("#specNoteError")
									.html(
											"*<b>Note</b> can have 10 to 150 chars[A-Za-z0-9.,-(space)]")
							$("#specNoteError").css('color', 'red');
							specNoteError = false;
						} else {
							$("#specNoteError").hide()

							specNoteError = true

						}
						return specNoteError
					}

					// register action event
					$("#specCode").keyup(function() {
						$(this).val($(this).val().toUpperCase());
						validate_specCode();

					})
					$("#specName").keyup(function() {
						validate_specName()

					})
					$("#specNote").keyup(function() {

						validate_specNote();

					})

					$("#specForm").submit(function() {

						validate_specCode();
						validate_specName();
						validate_specNote();

						if (specCodeError && specNameError && specNoteError)
							return true
						else
							return false
					})

				})// document
