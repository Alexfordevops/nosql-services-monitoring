import { Component } from '@angular/core';
import {UploadComponent} from '../../features/images/upload-component/upload-component';
import {MessageForm} from '../../features/messages/message-form/message-form';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-page',
  standalone: true,
  imports: [
    UploadComponent,
    MessageForm,
    CommonModule
  ],
  templateUrl: './create-page.html',
  styleUrl: './create-page.scss'
})
export class CreatePage {

  constructor(private router: Router) {}

  goToSelect() {
    this.router.navigate(['/select']);
  }
}
