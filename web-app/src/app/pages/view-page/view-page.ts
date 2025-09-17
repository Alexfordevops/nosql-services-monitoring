import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {ExpositorComponent} from '../../features/expositor/expositor-component/expositor-component';
import {FileInfo} from '@angular/build/private';
import {MessageInterface} from '../../interfaces/MessageInterface';
import {ImageWithUrl} from '../../interfaces/ImageWithUrlInterface';

@Component({
  selector: 'app-view-page',
  standalone: true,
  imports: [
    CommonModule,
    ExpositorComponent
  ],
  templateUrl: './view-page.html',
  styleUrl: './view-page.scss'
})
export class ViewPage {

  image?: ImageWithUrl;
  message?: MessageInterface;

  ngOnInit(): void {
    const state = history.state;
    this.image = state.image;
    this.message = state.message;
  }
}
