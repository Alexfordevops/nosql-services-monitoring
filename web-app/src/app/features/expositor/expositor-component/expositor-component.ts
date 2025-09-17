import { Component, Input, OnChanges, SimpleChanges, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageInterface } from '../../../interfaces/MessageInterface';
import {ImageWithUrl} from '../../../interfaces/ImageWithUrlInterface';

@Component({
  selector: 'app-expositor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './expositor-component.html',
  styleUrls: ['./expositor-component.scss']
})
export class ExpositorComponent {

  @Input() image?: ImageWithUrl;
  @Input() message?: MessageInterface;
}
