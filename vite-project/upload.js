const $submitBtn =  document.querySelector(".submitButton");
function loadFile(input) {
    var file = input.files[0];	//선택된 파일 가져오기

    //미리 만들어 놓은 div에 text(파일 이름) 추가
    var name = document.getElementById('fileName');
    name.textContent = file.name;
}

function uploadImage() {
    const fileInput = document.getElementById('chooseFile');
    const file = fileInput.files[0]; // 선택한 파일 가져오기

    const formData = new FormData();
    formData.append('image', file); // 이미지 파일을 FormData에 추가
    formData.append('latitude', document.querySelector('.latlng').id);
    formData.append('longtitude',document.querySelector('.latlng').innerText);
    fetch('http://localhost:8080/api/user/upload', {
        method: 'POST',
        body: formData
    }).then(data => {
        if (data.ok){
            alert('success');
            window.location.reload();
        }else{
            alert('fail');
        }
    })
        .catch(error => {
            console.log('error', error);
        });

}



