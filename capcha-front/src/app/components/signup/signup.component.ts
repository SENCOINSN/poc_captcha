import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
} from '@angular/core';
import {
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';

import { RecaptchaModule } from 'ng-recaptcha';

import { SignServiceService } from '../../services/sign-service.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule,RecaptchaModule,FormsModule,ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  requestModel:any ={captchaToken:''}
  signinService = inject(SignServiceService)
  route=inject(Router)

  token:any;


  form: any = {
    firstName: null,
    lastName: null,
    email: null,
    password: null
  };

  loginForm!:FormGroup


  resolved(token: any) {
    //console.log("token "+token)
    this.requestModel.captchaToken = token;
}

onError(log: any) {
    console.log('error ', log);
}

onSubmit(){
  console.log("process signup")
  const object = {
    firstName:this.form.firstName,
    lastName:this.form.lastName,
    email:this.form.email,
    password:this.form.password,
    token:this.requestModel.captchaToken
  }

  //console.log(" payload "+ JSON.stringify(object))
  this.signinService.signUp(object)
  .subscribe({
    next:(data:any)=>{
      console.log("Successfully signup !!!")
      this.route.navigate(['home']);
    },
    error:(err:any)=>{
      console.log(err)
    }
  })
}

}
