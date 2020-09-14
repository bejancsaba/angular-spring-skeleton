import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-entry',
  template: `
        <router-outlet></router-outlet>`
})
export class EntryComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
  }
}
