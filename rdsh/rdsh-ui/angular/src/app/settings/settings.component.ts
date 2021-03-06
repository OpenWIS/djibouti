import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppComponent} from "../app.component";
import {SettingsService} from "../services/settings-service.service";
import {MatSnackBar} from "@angular/material";
import {LDSHDTO} from "../dto/LDSH.dto";
import {SettingDTO} from "../dto/Setting.dto";
import {Router} from "@angular/router";

@Component({
  selector: "app-settings",
  templateUrl: "./settings.component.html",
  styleUrls: ["./settings.component.css"]
})
export class SettingsComponent implements OnInit {
  settingsForm: FormGroup;
  hide :boolean = true;


  @ViewChild("readingTpSel") private readingTpSel: ElementRef;

  constructor(private fb: FormBuilder, private settingsService: SettingsService,
              public snackBar: MatSnackBar, private router: Router) {
    AppComponent.selectedMenuItem = "settings";

    // Render an empty form until data is loaded.
    this.settingsForm = this.fb.group({
      title: ['', [Validators.required]],
      email: ['', [Validators.required]],
      copyright: ['', [Validators.required]],
      header: ['', [Validators.required]],

      mqtt_host: ['', [Validators.required]],
      mqtt_username: ['', [Validators.required]],
      mqtt_password: ['', [Validators.required]],

      jwt_secret: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    this.settingsService.list().subscribe(
      onNext => {
        for (let setting of onNext) {
          this.settingsForm.controls[setting["settingKey"]].setValue(setting["settingVal"]);
        }
      }, onError => {
        console.error(onError);
        this.snackBar.open('Could not read system settings.', null, {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
  }

  ngAfterViewInit() {
    if (!AppComponent.menuOpen) {
      document.getElementById("sitenav").click();
    }
  }

  // We need to manually parse the value of the form here, since it can not be mapped to an object.
  onSubmit() {

    if (this.settingsForm.valid) {

      let jsonValue = JSON.parse(JSON.stringify(this.settingsForm.getRawValue()));
      var settings: SettingDTO[] = new Array<SettingDTO>();
      for (let key in jsonValue) {
        settings.push(new SettingDTO(key, jsonValue[key]));
      }

      this.settingsService.save(settings).subscribe(
        onNext => {
          this.snackBar.open('Settings saved successfully.', null, {
            duration: 5000,
            verticalPosition: 'top'
          });
          this.router.navigate(['/settings']);
        }, onError => {
          console.log(onError);
          this.snackBar.open('Settings could not be saved.', null, {
            duration: 5000,
            verticalPosition: 'top'
          });
        });
    }else {
      this.snackBar.open('Please enter a correct value to all required fields', null, {
        duration: 5000,
        verticalPosition: 'top'
      });
    }

  }
}
