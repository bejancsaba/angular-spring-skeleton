import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { WebSocketAPI } from './WebSocketAPI';
import { HttpParams, HttpClient } from '@angular/common/http';
import {AuthJwtServerProvider} from "./services/auth-jwt.service";
import {AclService} from "./services/acl.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AppComponent implements OnInit{
  webSocketAPI: WebSocketAPI;
  topic: string = "/topic/persons";
  persons: Person[];
  displayedColumns: string[] = ['name', 'age'];
  title = 'UI demo app';
  clickMessage = '';
  userName = '';


  constructor(private http: HttpClient, private authProvider: AuthJwtServerProvider, private aclService: AclService) {
    this.webSocketAPI = new WebSocketAPI(this.topic, x => this.handleMessage(x));
    this.webSocketAPI._connect();
    this.userName = authProvider.getIdentity().sub;
  }

  ngOnInit(): void {
    this.getPersons()
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

  onPersist(name: HTMLInputElement, age: HTMLInputElement) {
    this.http.post<Person>("/person", new Person(name.value, age.value))
        .subscribe(
            response  => {
              name.value = '';
              age.value = '';
            },
            error => this.clickMessage = error.json
        );
  }

  getPersons() {
    return this.http.get<Person[]>("/person/all").subscribe(
        response => this.persons = response,
        error => this.clickMessage = error.json
    )
  }

  handleMessage(personMessage: string) {
    this.persons = JSON.parse(personMessage).persons;
  }

  isAdmin(): boolean {
    return this.aclService.hasRole('create');
  }
}

export interface DemoResponse {
  text: string;
}

export class Person {
  name = '';
  age = '';

  constructor(name: string, age: string) {
    this.name = name
    this.age = age
  }
}
