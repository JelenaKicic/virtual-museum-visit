import { Component, OnInit } from '@angular/core';
import { Logs } from '../models/logs.model';
import { LogsService } from '../services/logs.service';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css'],
})
export class LogsComponent implements OnInit {
  public displayedColumns: string[] = ['username', 'time', 'action'];
  public logs: Logs[] = [];

  constructor(private logsService: LogsService) {}

  ngOnInit(): void {
    this.logsService.getLogs().subscribe((value) => {
      this.logs = value;
    });
  }

  downloadLogsPdf() {
    this.logsService.getLogsAsPdf().subscribe((data) => {
      var downloadURL = window.URL.createObjectURL(data);
      var link = document.createElement('a');
      link.href = downloadURL;
      link.download = 'logs.pdf';
      link.click();
    });
  }
}
