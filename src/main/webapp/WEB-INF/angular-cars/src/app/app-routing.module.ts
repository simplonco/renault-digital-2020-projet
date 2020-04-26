import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {CarModelListComponent} from "./car-model-list/car-model-list.component";
import {CarFormComponent} from "./car-form/car-form.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  {path: "register", component: RegisterComponent},
  {path: "login", component: LoginComponent},
  {path: "logout", component: LogoutComponent},
  {path: "create", component: CarFormComponent},
  {path: "list", component: CarModelListComponent},
  {path: "cars/:brand", component: CarModelListComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
