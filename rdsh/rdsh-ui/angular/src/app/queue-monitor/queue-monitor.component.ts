import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatSnackBar } from "@angular/material";
import { RouterModule, Routes, Router } from "@angular/router";
import { ChannelService } from '../services/channel.service';
import { MqttTopic } from '../dto/MqttTopic.dto';


@Component({
  selector: 'app-queue-monitor',
  templateUrl: './queue-monitor.component.html',
  styleUrls: ['./queue-monitor.component.css']
})
export class QueueMonitorComponent implements OnInit {
  displayedColumns = ["name", "messagesSent", "bytesSent", "failedConnections", "Actions"];

  dataSource = null;
  interval: any;

  constructor(private channelService: ChannelService, private router: Router, public snackBar: MatSnackBar) { }

  ngOnInit() {

    this.getAllChannels();
    this.interval = setInterval(() => {
      this.refresh();
    }, 5000);
  }

  private getAllChannels() {

    this.channelService.list().subscribe(
      onNext => {
        this.buildChart(onNext);
        this.dataSource = new MatTableDataSource<MqttTopic>(onNext);
      }, onError => {
        console.error(onError);
        this.snackBar.open('There was a problem fetching the list of Channels.', null, {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
  }

  gotoQueue(id: string) {
    this.router.navigate(['/channelView'], { queryParams: { "id": id } });
  }

  back() {
    this.router.navigate(["/channels"]);
  }

  refresh() {
    // this.getAllChannels();
    this.channelService.list().subscribe(
      onNext => {

        if (onNext.length != this.barChartLabels.length) {
          // RE-render in case were there is a new Channel.
          this.buildChart(onNext);
          this.dataSource = new MatTableDataSource<MqttTopic>(onNext);
        } else {
          this.updateChart(onNext);
        }
        this.dataSource = new MatTableDataSource<MqttTopic>(onNext);
      }, onError => {
        console.error(onError);
        this.snackBar.open('There was a problem fetching the list of Channels.', null, {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
  }

  purge(id: string) {
    this.channelService.purge(id).subscribe(onNext => {
      this.snackBar.open('Channel data cleared.', null, {
        duration: 5000,
        verticalPosition: 'top'
      });

      this.refresh();

    }, onError => {
      console.error(onError);
      this.snackBar.open('There was a problem fetching the list of Channels.', null, {
        duration: 5000,
        verticalPosition: 'top'
      });
    });
  }

  //charts
  public barChartLabels: string[] = [];
  public barChartType: string = 'bar';
  public barChartLegend: boolean = true;

  public bytesChartOptions: any = {

    scaleShowVerticalLines: false,
    responsive: true,

    scales: {
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Bytes'
        },
        ticks: {
          beginAtZero: true,
        }
      }]
    }
  };

  public msgsChartOptions: any = {

    scaleShowVerticalLines: false,
    responsive: true,

    scales: {
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Messages'
        },
        ticks: {
          beginAtZero: true,
        }
      }]
    }
  };

  public lineChartColors: Array<any> = [
    {
      backgroundColor: 'rgba(128,130,177,0.8)',
      pointBackgroundColor: 'rgba(118,131,177,1)',
      pointBorderColor: '#ffA',
      pointHoverBackgroundColor: '#fffA',
      pointHoverBorderColor: 'rgba(48,159,177,0.8)'
    },
  ];

  public msgChartData: any[] = [
    { data: [], label: 'Messages Sent' },
    { data: [], label: 'Failed Connections' }
  ];

  public bytesChartData: any[] = [
    { data: [], label: 'Bytes Sent' }
  ];

  private buildChart(array: any[]) {
    this.barChartLabels = [];
    this.msgChartData[0].data = [];
    this.msgChartData[1].data = [];
    this.bytesChartData[0].data = [];

    for (let channel of array) {
      this.barChartLabels.push(channel.channelName);
      this.msgChartData[0].data.push(channel.msessagesSent);
      this.msgChartData[1].data.push(channel.failedConnections);
      this.bytesChartData[0].data.push(channel.bytesSent);
    }

  }


  // we use update via temp variables to avoid re-rendering
  private updateChart(array: any[]) {
    let msgTmpData: any[] = [
      { data: [], label: 'Messages Sent' },
      { data: [], label: 'Failed Connections' }
    ];

    let bytesTmptData: any[] = [
      { data: [], label: 'Bytes Sent' }
    ];
    for (let channel of array) {
      msgTmpData[0].data.push(channel.msessagesSent);
      msgTmpData[1].data.push(channel.failedConnections);
      bytesTmptData[0].data.push(channel.bytesSent);
    }
    this.msgChartData = msgTmpData;
    this.bytesChartData = bytesTmptData;
  }

}
