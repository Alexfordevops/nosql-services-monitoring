import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {GalleryComponent} from '../../features/images/gallery-component/gallery-component';
import {MessageComponent} from '../../features/messages/message-component/message-component';
import {FileInfo} from '../../interfaces/FileInfoInterface';
import {MessageInterface} from '../../interfaces/MessageInterface';
import {Router} from '@angular/router';
import {ImageWithUrl} from '../../interfaces/ImageWithUrlInterface';

@Component({
  selector: 'app-select-page',
  standalone: true,
  imports: [
    CommonModule,
    GalleryComponent,
    MessageComponent
  ],
  templateUrl: './select-page.html',
  styleUrl: './select-page.scss'
})
export class SelectPage {

  selectedImage: ImageWithUrl | null = null;
  selectedMessage: MessageInterface | null = null;

  constructor(private router: Router) {}

  onImageSelected(img: ImageWithUrl) {
    this.selectedImage = img;
  }

  onMessageSelected(msg: MessageInterface) {
    this.selectedMessage = msg;
  }

  goToView() {
    if (this.selectedImage && this.selectedMessage) {
      this.router.navigate(['/view'], { state: { image: this.selectedImage, message: this.selectedMessage }});
    }
  }
}
