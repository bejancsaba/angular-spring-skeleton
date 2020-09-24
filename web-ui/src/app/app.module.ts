import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './your-first-module/app.component';
import { EntryComponent } from './entry.component';
import { LoginComponent } from "./your-first-module/login.component";
import { LoginService } from './your-first-module/services/login.service';
import { MaterialModule } from './material/material.module';
import { User } from "./your-first-module/services/user.service";
import { AclService } from "./your-first-module/services/acl.service";
import { AuthJwtServerProvider } from "./your-first-module/services/auth-jwt.service";
import {PageAccessGuard} from "./your-first-module/services/page-access-guard.service";
import {UrlCacheService} from "./your-first-module/services/url-cache.service";
import {JwtInterceptor} from "./your-first-module/services/jwt-interceptor";
import {TopicComponent} from "./your-first-module/topic.component";

@NgModule({
  declarations: [
    EntryComponent,
    LoginComponent,
    TopicComponent,
    AppComponent
  ],
  exports: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [
    AclService,
    AuthJwtServerProvider,
    LoginService,
    PageAccessGuard,
    UrlCacheService,
    User,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ],
  bootstrap: [EntryComponent],
  entryComponents: [
    EntryComponent
  ]
})
export class AppModule { }
