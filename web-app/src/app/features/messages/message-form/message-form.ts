import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService } from '../../../services/message-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-message-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './message-form.html',
  styleUrls: ['./message-form.scss']
})
export class MessageForm {

  messageForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private router: Router
  ) {
    this.messageForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      message: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  onSubmit() {
    if (this.messageForm.valid) {
      this.messageService.create(this.messageForm.value).subscribe({
        next: () => {
          this.successMessage = 'Mensagem enviada com sucesso!';
          this.errorMessage = '';
          this.messageForm.reset();
        },
        error: (err) => {
          this.errorMessage = 'Erro ao salvar mensagem: ' + (err.message || '');
          this.successMessage = '';
          console.error(err);
        }
      });
    }
  }
}
