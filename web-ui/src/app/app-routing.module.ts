import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './your-first-module/app.component';

const routes: Routes = [
  {
    path: '',
    component: AppComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
