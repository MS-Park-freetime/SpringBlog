let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{//function()대신 사용한 이유는 this를 바인딩하기 위해
			this.save();
		});
		$("#btn-update").on("click", ()=>{//function()대신 사용한 이유는 this를 바인딩하기 위해
			this.update();
		});
		/*$("#btn-login").on("click", ()=>{//function()대신 사용한 이유는 this를 바인딩하기 위해
			this.login();
		});*/
	},
	
	save: function(){
		//alert('확인');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		//console.log(data);
		//ajax호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환해주는 기능이 생긴듯?
		$.ajax({
			//회원가입 수행요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json"//요청을 서버로 하여 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) javascript오브젝트로 변경
			//생략해도될듯..
		}).done(function(resp){
			if(resp.status === 500){
				alert("중복된 아이디 입니다.");
			}else{
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); //ajax를 이용해 3개의 데이터를 json으로 변경하여 insert 요청
	},
	
	/*login: function(){
		//alert('확인');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		//console.log(data);
		//ajax호출시 default가 비동기 호출
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환해주는 기능이 생긴듯?
		$.ajax({
			//회원가입 수행요청
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지
			dataType: "json"//요청을 서버로 하여 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) javascript오브젝트로 변경
			//생략해도될듯..
		}).done(function(resp){
			alert("로그인이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); //ajax를 이용해 3개의 데이터를 json으로 변경하여 insert 요청
	}*/
	
	update: function(){
		//alert('확인');
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("회원정보 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); //ajax를 이용해 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();