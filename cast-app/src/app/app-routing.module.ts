import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CursoListComponent } from './component/curso-list/curso-list.component';
import { CategoriaListComponent } from './component/categoria-list/categoria-list.component';

const routes: Routes = [
  { path: 'cursos', component: CursoListComponent },
  { path: 'categorias', component: CategoriaListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
