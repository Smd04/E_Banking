import {
  Component,
  ElementRef,
  ViewChild,
  AfterViewInit,
  HostListener,
  Input
} from '@angular/core';
import * as d3 from 'd3';
import { Compte } from '../../models/models-client/Compte';

@Component({
  selector: 'app-compte-chart',
  standalone: true,
  template: `<div #chartContainer style="width: 100%; height: 400px;"></div>`,
})
export class CompteChartComponent implements AfterViewInit {
  @ViewChild('chartContainer') chartContainer!: ElementRef;

  @Input() data: { month: string, balance: number }[] = [];
  @Input() account!: string | undefined;

  @HostListener('window:resize')
  onResize() {
    this.createChart();
  }

  ngAfterViewInit(): void {
    this.createChart();
  }

  private createChart(): void {
    const element = this.chartContainer.nativeElement;
    const margin = { top: 30, right: 30, bottom: 40, left: 60 };

    const width = element.clientWidth - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Supprime l'ancien SVG
    d3.select(element).select('svg').remove();

    const svg = d3.select(element)
      .append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
      .attr('transform', `translate(${margin.left},${margin.top})`);

    // Échelles
    const x = d3.scaleBand()
      .domain(this.data.map(d => d.month))
      .range([0, width])
      .padding(0.2);

    const y = d3.scaleLinear()
      .domain([0, d3.max(this.data, d => d.balance)! * 1.1])
      .range([height, 0]);

    // Axes
    svg.append('g')
      .attr('transform', `translate(0,${height})`)
      .call(d3.axisBottom(x));

    svg.append('g')
      .call(d3.axisLeft(y).tickFormat((d, i) =>
        `${d3.format(',.0f')(+d)} ${this.account ?? ''}`
      ));



    // Barres
    const bars = svg.selectAll('.bar')
      .data(this.data)
      .enter()
      .append('rect')
      .attr('class', 'bar')
      .attr('x', d => x(d.month)!)
      .attr('width', x.bandwidth())
      .attr('y', height)
      .attr('height', 0)
      .attr('fill', '#4e79a7');

    // Animation
    bars.transition()
      .duration(800)
      .attr('y', d => y(d.balance))
      .attr('height', d => height - y(d.balance))
      .delay((_, i) => i * 100);

    // Ligne de moyenne (optionnelle)
    const average = d3.mean(this.data, d => d.balance)!;
    svg.append('line')
      .attr('x1', 0)
      .attr('x2', width)
      .attr('y1', y(average))
      .attr('y2', y(average))
      .attr('stroke', '#e63946')
      .attr('stroke-dasharray', '4')
      .attr('stroke-width', 1.5);

    svg.append('text')
      .attr('x', width - 5)
      .attr('y', y(average) - 8)
      .attr('text-anchor', 'end')
      .attr('fill', '#e63946')
      .attr('font-size', '12px')
      .text(`Moyenne: ${average.toFixed(0)} ${this.account ?? ''}`);
  }
}
