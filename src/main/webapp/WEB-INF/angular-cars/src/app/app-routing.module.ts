import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CarModelListComponent} from "./car-model-list/car-model-list.component";


const routes: Routes = [
  {path: "cars/:brand", component: CarModelListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
