import { Routes } from '@angular/router';
import {SelectPage} from './pages/select-page/select-page';
import {ViewPage} from './pages/view-page/view-page';

export const routes: Routes = [
  { path: '', redirectTo: 'select', pathMatch: 'full' },
  { path: 'select', component: SelectPage },
  { path: 'view', component: ViewPage },
];
