import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './your-first-module/app.component';
import {LoginComponent} from "./your-first-module/login.component";
import {PageAccessGuard} from "./your-first-module/services/page-access-guard.service";
import {TopicComponent} from "./your-first-module/topic.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: AppComponent,
    canActivate: [PageAccessGuard]
  },
  {
    path: `topic/:topicId`,
    component: TopicComponent,
    canActivate: [PageAccessGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
