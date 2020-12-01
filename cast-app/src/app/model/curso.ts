import { Categoria } from './categoria';

export class Curso {
  codigo: number;
  assunto: string;
  dataInicio: any;
  dataTermino: any;
  quantidadeAlunosPorTurma?: number;
  categoria: Categoria = new Categoria();
}
