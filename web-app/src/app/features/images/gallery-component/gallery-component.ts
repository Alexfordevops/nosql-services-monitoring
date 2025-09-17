import { Component, EventEmitter, OnInit, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ImageService } from '../../../services/image-service';
import { FileInfo } from '../../../interfaces/FileInfoInterface';
import { map } from 'rxjs/operators';

interface ImageWithUrl {
  fileInfo: FileInfo;
  url: SafeUrl;
}

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

  constructor(private imageService: ImageService, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.imageService.listAll().subscribe({
      next: (res) => {
        res.forEach(fileInfo => {
          this.imageService.download(fileInfo.id).pipe(
            map(blob => {
              const objectUrl = URL.createObjectURL(blob);
              return { fileInfo, url: this.sanitizer.bypassSecurityTrustUrl(objectUrl) } as ImageWithUrl;
            })
          ).subscribe(imgWithUrl => {
            this.images.set([...this.images(), imgWithUrl]);
          });
        });
      },
      error: (err) => console.error('Erro ao carregar imagens', err)
    });
  }

  selectImage(img: ImageWithUrl) {
    this.selectedImage = img;
    this.imageSelected.emit(img);
  }
}
