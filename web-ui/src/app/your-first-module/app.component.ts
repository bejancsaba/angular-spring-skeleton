import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AppComponent {
  title = 'UI demo app';
  clickMessage = '';

  constructor(private http: HttpClient) {
  }

  onSayMyName(name: string, times: string) {
    let params = new HttpParams();
    params = params.append('name', name);
    params = params.append('times', times);

    this.http.get<DemoResponse>("/myname", {params: params})
      .subscribe(
        response  => this.clickMessage = response.text,
        error => this.clickMessage = error.json
      );
  }
}

export interface DemoResponse {
  text: string;
}
