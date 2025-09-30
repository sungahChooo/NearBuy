// 웹 유효성검사 라이브러리
//		최대한 다양한 상황에서 쓸 수 있게
// 긍정/부정 컨셉 통일

// input을 넣었을때
// 안썼으면 true, 썼으면 false
function isEmpty(input) {
	return !input.value;
}

// input, 글자수 넣었을때
// 짧으면 true, 아니면 true
function lessThan(input, len) {
	return input.value.length < len;
}

// input 넣었을때
// 한글/특수문자가 있으면 true, 없으면 false
//		-_@.은 괜찮음
function containsHS(input) {
	var hs = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-_.@";
	for (var i = 0; i < input.value.length; i++) {
		// input 값이 hs에 포함되어 있는 값이 아니면(한글/특수문자 있으면)
		if (hs.indexOf(input.value[i]) == -1) {
			return true;
		}
	}
	return false;
}

function notEquals(input, inputChk) {
	return (input.value != inputChk.value);
}

// input, 문자열 세트 넣었을때
// 넣은 값이 안들어있으면 true, 들어있으면 false
function notContains(input, set) {
	for (var i = 0; i < set.length; i++) {
		if (input.value.indexOf(set[i]) != -1) {
			return false;
		}
	}
	return true;
}

// input 넣었을때
// 숫자 아니면 true, 숫자면 false
function isNotNum(input) {
	return input.value.indexOf(" ") != -1 || isNaN(input.value);
}

// input, 확장자를 넣었을때
// 그 파일이 아니면 true, 맞으면 false
function isNotType(input, type){
	type = "." + type;
	return input.value.toLowerCase().indexOf(type) == -1;
}

