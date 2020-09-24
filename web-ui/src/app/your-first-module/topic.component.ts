import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {WebSocketAPI} from "./WebSocketAPI";

@Component({
  selector: 'topic-component',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class TopicComponent implements OnInit {
  webSocketAPI: WebSocketAPI;
  topicRoot = '/topic/';
  topic = '';
  message = '';

  constructor(private http: HttpClient, private route: ActivatedRoute) {
    this.route.params.subscribe(params => this.topic = params['topicId']);
    this.webSocketAPI = new WebSocketAPI(this.topicRoot + this.topic, x => this.handleMessage(x));
    this.webSocketAPI._connect();
  }

  ngOnInit(): void {
  }


  private handleMessage(x: string) {
    this.message = x;
  }
}
