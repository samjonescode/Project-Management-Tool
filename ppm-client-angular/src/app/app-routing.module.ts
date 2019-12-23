import { NgModule } from "@angular/core";
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { Routes, RouterModule } from '@angular/router';
import { ProjectFormComponent } from './project-form/project-form.component';


const routes = [{path:'dashboard', component: DashboardComponent},
{path:'register',component:RegisterComponent},
{path:'login',component:LoginComponent},
{path: 'projectForm', component: ProjectFormComponent}]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule{}
