import { Component, EventEmitter, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageService } from '../../../services/image-service';
import { FileInfo } from '../../../interfaces/FileInfoInterface';
import { ImageWithUrl } from '../../../interfaces/ImageWithUrlInterface';

@Component({
  selector: 'app-gallery-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gallery-component.html',
  styleUrls: ['./gallery-component.scss']
})
export class GalleryComponent implements OnInit {

  images = signal<ImageWithUrl[]>([]);
  selectedImage: ImageWithUrl | null = null;

  @Output() imageSelected = new EventEmitter<ImageWithUrl>();

  constructor(private imageService: ImageService) {}

  ngOnInit(): void {
    this.imageService.listAll().subscribe({
      next: (res) => {
        res.forEach(fileInfo => {
          this.imageService.download(fileInfo.id).subscribe(blob => {
            const objectUrl = URL.createObjectURL(blob); // cria URL do blob
            const imgWithUrl: ImageWithUrl = { fileInfo, url: objectUrl };
            this.images.set([...this.images(), imgWithUrl]); // atualiza signal
          });
        });
      },
      error: (err) => console.error('Erro ao carregar imagens', err)
    });
  }

  selectImage(img: ImageWithUrl) {
    this.selectedImage = img;
    this.imageSelected.emit(img); // envia imagem selecionada para o parent
  }
}
