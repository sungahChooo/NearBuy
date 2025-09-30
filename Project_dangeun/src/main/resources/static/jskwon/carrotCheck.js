function uploadCheck(form) {
	var photoField = form.photoo;
	var nameField = form.name;
	var priceField = form.price;
	var descField = form.description;
	var cateField = form.category;

	if (isEmpty(nameField)) {
		alert("상품명을 입력하세요.");
		nameField.focus();
		return false;
	}
	if (isNotType(photoField, "png") && isNotType(photoField, "jpg") && !isEmpty(photoField)) {
		alert("사진 파일이 아닙니다.");
		photoField.value = "";
		return false;
	}
	if (isNotNum(priceField) || isEmpty(priceField)) {
		alert("가격을 제대로 입력하세요.");
		priceField.value = "";
		priceField.focus();
		return false;
	}
	if (isEmpty(descField)) {
		alert("상품 설명을 입력하세요.");
		descField.focus();
		return false;
	}

	if (cateField.value === "카테고리를 선택하세요." || cateField.value === "---------------------------------") {
		alert("카테고리를 선택하세요.");
		return false;
	}

	return true;

}

function updateCheck(form) {
	var photoField = form.photoo;
	var nameField = form.name;
	var priceField = form.price;
	var descField = form.description;
	var cateField = form.category;
	
	if (isEmpty(nameField)) {
		alert("상품명을 입력하세요.");
		nameField.focus();
		return false;
	}
	if (isNotType(photoField, "png") && isNotType(photoField, "jpg") && !isEmpty(photoField)) {
		alert("사진 파일이 아닙니다.");
		photoField.value = "";
		return false;
	}
	if (isNotNum(priceField) || isEmpty(priceField)) {
		alert("가격을 제대로 입력하세요.");
		priceField.value = "";
		priceField.focus();
		return false;
	}
	
	if (isEmpty(descField)) {
		alert("상품 설명을 입력하세요.");
		descField.focus();
		return false;
	}

	if (cateField.value === "카테고리를 선택하세요." || cateField.value === "---------------------------------") {
		alert("카테고리를 선택하세요.");
		return false;
	}

	return true;
}

function reportCheck(form) {
	var cateField = form.category;
	var titleField = form.title;
	var textField = form.text;
	var photoField = form.photoo;
	
	if (cateField.value === "카테고리를 선택하세요." || cateField.value === "---------------------------------") {
		alert("카테고리를 선택하세요.");
		return false;
	}
	
	if (isEmpty(titleField)) {
		alert("제목을 입력하세요.");
		titleField.focus();
		return false;
	}
	
	if (isEmpty(textField)) {
		alert("신고 내용을 입력하세요.");
		textField.focus();
		return false;
	}
	
	if (isNotType(photoField, "png") && isNotType(photoField, "jpg") && !isEmpty(photoField)) {
		alert("사진 파일이 아닙니다.");
		photoField.value = "";
		return false;
	}
	
	return true;
}