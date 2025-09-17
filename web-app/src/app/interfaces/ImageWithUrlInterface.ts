// ImageWithUrlInterface.ts
import { FileInfo } from './FileInfoInterface';
import { SafeUrl } from '@angular/platform-browser';

export interface ImageWithUrl {
  fileInfo: FileInfo; // metadados da imagem
  url: SafeUrl;       // URL segura gerada para exibição no front-end
}
