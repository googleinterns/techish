function setForm(value) {
    var studentForm = document.getElementById('student');
    var professionalForm = document.getElementById('professional');
    if (studentForm && professionalForm) {
        if (value == 'student') {
            studentForm.setAttribute('style', 'display:block;');
            professionalForm.setAttribute('style', 'display:none;');
        }
        else {
            professionalForm.setAttribute('style', 'display:block;');
            studentForm.setAttribute('style', 'display:none;');
        }
    }
}
