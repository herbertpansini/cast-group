import { Component } from '@angular/core';
import { Categoria } from 'src/app/model/categoria';
import { CategoriaService } from 'src/app/service/categoria.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-categoria-list',
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.css'],
  providers: [ MessageService, ConfirmationService ]
})
export class CategoriaListComponent {
    categoriaDialog: boolean;

    categorias: Categoria[];

    categoria: Categoria;

    searchCategoria: string = '';

    totalRecords: number;

    loading: boolean;

    submitted: boolean;

    constructor(private categoriaService: CategoriaService,
                private messageService: MessageService,
                private confirmationService: ConfirmationService) { }

    loadCategoriesByPage(event?) {
        this.loading = true;
        setTimeout(() => {
            this.categoriaService.findAll(this.searchCategoria, (event.first / event.rows), event.rows).subscribe(response => {
                this.categorias = response.content;
                this.totalRecords = response.totalElements;
                this.loading = false;
              });
        }, 1000);
    }

    openNew() {
        this.categoria = new Categoria();
        this.submitted = false;
        this.categoriaDialog = true;
    }

    editCategoria(categoria: Categoria) {
        this.categoria = {...categoria};
        this.categoriaDialog = true;
    }

    deleteCategoria(categoria: Categoria) {
        this.confirmationService.confirm({
            message: 'Você deseja excluir categoria: ' + categoria.descricao + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.categoriaService.delete(categoria.codigo).subscribe(
                response => {
                  this.categorias = this.categorias.filter(val => val.codigo !== categoria.codigo);
                });
                this.messageService.add({severity:'success', summary: 'Successful', detail: 'Categoria excluída', life: 3000});
            }
        });
    }

    hideDialog() {
        this.categoriaDialog = false;
        this.submitted = false;
    }

    saveCategoria() {
        this.submitted = true;

        if (this.categoria.descricao.trim()) {
            if (this.categoria.codigo) {
                this.categoriaService.update(this.categoria.codigo, this.categoria).subscribe(response => {
                  this.categoria = response;
                  this.categorias[this.findIndexById(this.categoria.codigo)] = this.categoria;
                });
                this.messageService.add({severity:'success', summary: 'Successful', detail: 'Categoria atualizada', life: 3000});
            } else {
                this.categoriaService.save(this.categoria).subscribe(response => {
                  this.categoria = response;
                  this.categorias.push(this.categoria);
                });
                this.messageService.add({severity:'success', summary: 'Successful', detail: 'Categoria cadastrada', life: 3000});
            }

            this.categorias = [...this.categorias];
            this.categoriaDialog = false;
            this.categoria = new Categoria();
        }
    }

    findIndexById(codigo: number): number {
        let index = -1;
        for (let i = 0; i < this.categorias.length; i++) {
            if (this.categorias[i].codigo === codigo) {
                index = i;
                break;
            }
        }
        return index;
    }
}
