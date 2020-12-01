import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/model/curso';
import { CursoService } from 'src/app/service/curso.service';
import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { CategoriaService } from 'src/app/service/categoria.service';
import { Categoria } from 'src/app/model/categoria';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-curso-list',
  templateUrl: './curso-list.component.html',
  styleUrls: ['./curso-list.component.css'],
  providers: [ MessageService, ConfirmationService ]
})
export class CursoListComponent implements OnInit {

    cursoDialog: boolean;

    cursos: Curso[];

    categorias: Categoria[];

    curso: Curso;

    searchCurso: string = '';

    totalRecords: number;

    loading: boolean;

    submitted: boolean;

    constructor(private cursoService: CursoService,
                private categoriaService: CategoriaService,
                private messageService: MessageService,
                private confirmationService: ConfirmationService) { }

    ngOnInit() {
      this.loadCategorias();
    }

    loadCategorias() {
      this.categoriaService.findAllCategorias().subscribe(response => this.categorias = response);
    }

    loadCursosByPage(event?) {
        this.loading = true;
        setTimeout(() => {
            this.cursoService.findAll(this.searchCurso, (event.first / event.rows), event.rows).subscribe(response => {
                this.cursos = response.content;
                this.totalRecords = response.totalElements;
                this.loading = false;
              });
        }, 1000);
    }

    openNew() {
        this.curso = new Curso();
        this.submitted = false;
        this.cursoDialog = true;
    }

    closeDialog() {
      this.cursos = [...this.cursos];
      this.cursoDialog = false;
      this.curso = new Curso();
    }

    editCurso(curso: Curso) {
        this.curso = {...curso};
        this.curso.dataInicio ? this.curso.dataInicio = new Date(`${this.curso.dataInicio}T00:00:00-03:00`) : '';
        this.curso.dataTermino ? this.curso.dataTermino = new Date(`${this.curso.dataTermino}T00:00:00-03:00`) : '';
        this.cursoDialog = true;
    }

    deleteCurso(curso: Curso) {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + curso.assunto + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.cursoService.delete(curso.codigo).subscribe(
                response => {
                  this.cursos = this.cursos.filter(val => val.codigo !== curso.codigo);
                });
                this.messageService.add({severity:'success', summary: 'Successful', detail: 'Curso excluído', life: 3000});
            }
        });
    }

    hideDialog() {
        this.cursoDialog = false;
        this.submitted = false;
    }

    saveCurso() {
        this.submitted = true;

        if (this.curso.assunto.trim()) {
            if (this.curso.codigo) {
                this.cursoService.update(this.curso.codigo, this.curso).subscribe(response => {
                  this.curso = response;
                  this.cursos[this.findIndexById(this.curso.codigo)] = this.curso;
                  this.messageService.add({severity:'success', summary: 'Successful', detail: 'Curso atualizado', life: 3000});
                  this.closeDialog();
                },
                (err) => {
                    let {error} = err;
                    this.messageService.add({severity:'error', summary: 'Error', detail: error.message, life: 3000});
                });
            } else {
                this.cursoService.save(this.curso).subscribe(response => {
                  this.curso = response;
                  this.cursos.push(this.curso);
                  this.messageService.add({severity:'success', summary: 'Successful', detail: 'Curso cadastrado', life: 3000});
                  this.closeDialog();
                },
                (err) => {
                  let {error} = err;
                    this.messageService.add({severity:'error', summary: 'Error', detail: error.message, life: 3000});
                });
            }
        }
    }

    findIndexById(codigo: number): number {
        let index = -1;
        for (let i = 0; i < this.cursos.length; i++) {
            if (this.cursos[i].codigo === codigo) {
                index = i;
                break;
            }
        }
        return index;
    }
}
