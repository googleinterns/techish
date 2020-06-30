function setForm(value: any) {
    const studentForm =  document.getElementById('student');
    const professionalForm = document.getElementById('professional')

    if(studentForm && professionalForm){
        if(value == 'student') {
            studentForm.setAttribute('style','display:block;');
            professionalForm.setAttribute('style','display:none;');
        }
        else {
            professionalForm.setAttribute('style','display:block;');
            studentForm.setAttribute('style','display:none;');
        }
    }
}