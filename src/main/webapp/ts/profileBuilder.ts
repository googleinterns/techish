function setForm(value: string) {
    const menteeForm =  document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor');
    const userTypeButton = document.getElementById('user-input');

    if(menteeForm && mentorForm && userTypeButton){
        if(value == 'Mentee') {
            menteeForm.setAttribute('style','display:block;');
            mentorForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentee');
        }
        else {
            mentorForm.setAttribute('style','display:block;');
            menteeForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentor');
        }
    }
} 