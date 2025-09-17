import { Component, EventEmitter, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageService } from '../../../services/message-service';
import { MessageInterface } from '../../../interfaces/MessageInterface';

@Component({
  selector: 'app-message-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message-component.html',
  styleUrls: ['./message-component.scss']
})
export class MessageComponent implements OnInit {

  messages = signal<MessageInterface[]>([]);
  selectedMessage: MessageInterface | null = null; // inicializado como null

  @Output() messageSelected = new EventEmitter<MessageInterface>();

  constructor(private messageService: MessageService) {}

  ngOnInit(): void {
    this.messageService.listAll().subscribe({
      next: (res) => {
        console.log('Mensagens carregadas:', res); // veja se estão corretas
        this.messages.set(res);
      },
      error: (err) => console.error(err)
    });
  }

  selectMessage(msg: MessageInterface) {
    this.selectedMessage = msg;
    this.messageSelected.emit(msg);
  }
}
