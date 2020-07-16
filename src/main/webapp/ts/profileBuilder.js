function setForm(value) {
    var menteeForm = document.getElementById('Mentee');
    var mentorForm = document.getElementById('Mentor');
    var userTypeButton = document.getElementById('user-input');
    if (menteeForm && mentorForm && userTypeButton) {
        if (value == 'Mentee') {
            menteeForm.setAttribute('style', 'display:block;');
            mentorForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentee');
        }
        else {
            mentorForm.setAttribute('style', 'display:block;');
            menteeForm.setAttribute('style', 'display:none;');
            userTypeButton.setAttribute('value', 'Mentor');
        }
    }
}
