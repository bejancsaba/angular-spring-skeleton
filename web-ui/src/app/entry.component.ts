import {Component, OnInit} from '@angular/core';
import {User} from "./your-first-module/services/user.service";
import {LoginService} from "./your-first-module/services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-entry',
  template: `
        <button mat-raised-button style="position:absolute;right: 30px" *ngIf=user.isAuthenticated() (click)=logout()>Logout {{sub}}</button>
        <router-outlet></router-outlet>`
})
export class EntryComponent implements OnInit {

  constructor(public user: User, private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(["/login"]);
  }

  get sub() {
    return this.user.identity()?.sub;
  }
}
