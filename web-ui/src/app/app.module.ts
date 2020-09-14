import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './your-first-module/app.component';
import { EntryComponent } from './entry.component';

@NgModule({
  declarations: [
    EntryComponent,
    AppComponent
  ],
  exports: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
  ],
  bootstrap: [EntryComponent],
  entryComponents: [
    EntryComponent
  ]
})
export class AppModule { }
