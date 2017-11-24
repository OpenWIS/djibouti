import { ModuleWithProviders } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

// component list:
import { DatasetsAdminComponent } from "./datasets-admin/datasets-admin.component";
import { SubmitFormComponent } from "./submit-form/submit-form.component";
import { RDSHComponent } from "./rdsh/rdsh.component";
import { SettingsComponent } from "./settings/settings.component";
import { DatasetsMainComponent } from "./datasets-main/datasets-main.component";

export const routerConfig: Routes = [
  {
    path: "home",
    component: DatasetsMainComponent  
  },
    {
    path: "datasets",
    component: DatasetsAdminComponent
  },
  {
    path: "rdsh",
    component: RDSHComponent
  },
  {
    path: "settings",
    component: SettingsComponent
  },
  {
    path: "submit",
    component: SubmitFormComponent
  },
  // edit mode
  {
    path: "submit/:id",
    component: SubmitFormComponent
  },
  {
    path: "about",
    component: SubmitFormComponent
  },
  {
    path: "",
    redirectTo: "/home",
    pathMatch: "full"
  },
  {
    path: "**",
    redirectTo: "/home",
    pathMatch: "full"
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routerConfig);
