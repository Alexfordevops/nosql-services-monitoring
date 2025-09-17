import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageWithUrl } from '../../../interfaces/ImageWithUrlInterface';
import { MessageInterface } from '../../../interfaces/MessageInterface';

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
