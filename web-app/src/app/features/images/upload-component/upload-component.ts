import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {UploadResponse} from '../../../interfaces/UploadResponseInterface';
import {ImageService} from '../../../services/image-service';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-upload-component',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './upload-component.html',
  styleUrls: ['./upload-component.scss']
})
export class UploadComponent {

  selectedFile?: File;            // arquivo escolhido
  previewUrl?: SafeUrl;           // URL segura para pré-visualização
  uploadResponse?: UploadResponse; // resposta do backend

  constructor(
    private imageService: ImageService,
    private sanitizer: DomSanitizer
  ) {}

  // Ao selecionar arquivo, cria uma URL de preview
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;

      // Gera preview local (sem enviar pro backend ainda)
      const objectUrl = URL.createObjectURL(file);
      this.previewUrl = this.sanitizer.bypassSecurityTrustUrl(objectUrl);
    }
  }

  // Faz upload para o backend
  upload(): void {
    if (this.selectedFile) {
      this.imageService.upload(this.selectedFile).subscribe({
        next: (res) => {
          this.uploadResponse = res;
          // Após upload bem-sucedido, remove preview (opcional)
          this.previewUrl = undefined;
          this.selectedFile = undefined;
        },
        error: (err) => {
          console.error('Erro no upload', err);
        }
      });
    }
  }
}
