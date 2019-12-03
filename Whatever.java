Set<Point> line1Set = getLinePoints(input[0]);
Set<Point> line2Set = getLinePoints(input[1]);

line2Set.retainAll(line1Set);
int closestDist = Integer.MAX_VALUE;
for(Point p : line2Set) {
  int manDist = Math.abs(p.x) + Math.abs(p.y);
  if(manDist < closestDist) {
    closestDist = manDist;
  }
}

System.out.printf("The closest intersection occurs %d manhattan distance\n", closestDist);

private Set<Point> getLinePoints(String input) {
  int x = 0;
  int y = 0;
  String[] values = line.split(",");
  Set<Point> s = new HashSet<>();
  for(String element : values) {
    int xDir = 0;
    int yDir = 0;
    switch(element.charAt(0)) {
      case 'R':
        xDir = 1;
        break;
      case 'L':
        xDir = -1;
        break;
      case 'U':
        yDir = 1;
        break;
      case 'D':
        yDir = -1;
    }
    int dist = Integer.parseInt(element.substring(1));
    for(int i = 0; i < dist; i++) {
      set.add(new Point(x + xDir, y + yDir);
    }
  }
  return s;
}
