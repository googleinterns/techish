function setForm(value: any) {
    const menteeForm =  document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor')

    if(menteeForm && mentorForm){
        if(value == 'Mentee') {
            menteeForm.setAttribute('style','display:block;');
            mentorForm.setAttribute('style','display:none;');
        }
        else {
            mentorForm.setAttribute('style','display:block;');
            menteeForm.setAttribute('style','display:none;');
        }
    }
} 