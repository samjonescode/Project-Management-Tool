import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {
  projectForm: FormGroup;
  constructor(private fb: FormBuilder,
    private http:HttpClient) { }

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm(){
    this.projectForm = new FormGroup({
      'projectName': this.fb.control(null, [Validators.required]),
      'projectIdentifier': this.fb.control(null, [Validators.required]),
      'description': this.fb.control(null, [Validators.required, Validators.minLength(4), Validators.maxLength(5)]),
      'start_date': this.fb.control(null),
      'end_date': this.fb.control(null)
    })
  }

  onSubmit(){
    alert(this.projectForm.value)
    this.http.post("https://spring-react-app-sj.herokuapp.com/api/project/",this.projectForm.value).subscribe(data => {
      console.log(data);
    })
  }
}
