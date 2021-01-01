function displayImage(newimg){
	document.getElementById("image2").src = "images/Steg/"+newimg+".jpg";
}

function modalShow(){
	document.getElementById("modalContainer").style.display = "block";
	document.getElementById("bod").style.overflow = "hidden";
}

function modalHide(){
	document.getElementById("modalContainer").style.display = "none";
	document.getElementById("bod").style.overflow = "visible";
}
