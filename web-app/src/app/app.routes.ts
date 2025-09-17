import { Routes } from '@angular/router';
import {SelectPage} from './pages/select-page/select-page';
import {ViewPage} from './pages/view-page/view-page';
import {CreatePage} from './pages/create-page/create-page';

export const routes: Routes = [
  { path: '', redirectTo: 'create', pathMatch: 'full' },
  {path: 'create', component: CreatePage},
  { path: 'select', component: SelectPage },
  { path: 'view', component: ViewPage },
];
