import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RateService } from '../rate.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
 
  authRequest: any 
      = {
      username : '',
      password : '',
    };

  response: any;
 
  constructor(private rateService: RateService, private router: Router) {}

  ngOnInit(): void {
         window.localStorage.clear();
    this.getAccessToken(this.authRequest);
  }


  getAccessToken(authRequest: any) {
    let resp = this.rateService.generateToken(authRequest);
    resp.subscribe((data: any) => {
//      window.localStorage.clear();
      console.log('authRequest:', authRequest);
      console.log('resp:', data);
     window.localStorage.setItem("token", data);
      this.router.navigate(['rates']);
    });
  }

 

}


