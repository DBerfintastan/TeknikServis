import { IProduct } from "./IProduct";

export interface ISaleDTO {
  id: number;
  price: number;
  note: string;
  product: IProduct;
}
